package com.turpgames.ichigu.model.singlegame;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.model.game.CardDealer;
import com.turpgames.ichigu.utils.R;

public class SingleGameCardDealer extends CardDealer {
	// region

	private final static float moveDuration = 0.25f;

	private final static Vector tableDestination = new Vector(- Card.Width, (Game.getVirtualHeight() - Card.Height) / 2);
	private final static Vector selectedDestination = new Vector(Game.getVirtualWidth(), (Game.getVirtualHeight() - Card.Height) / 2);

	private final static Vector[][] routes = new Vector[][] {
			new Vector[] { R.learningModeScreen.layout.cardOnTable1Pos, tableDestination },
			new Vector[] { R.learningModeScreen.layout.cardOnTable2Pos, tableDestination },
			new Vector[] { R.learningModeScreen.layout.cardToSelect1Pos, selectedDestination },
			new Vector[] { R.learningModeScreen.layout.cardToSelect2Pos, selectedDestination },
			new Vector[] { R.learningModeScreen.layout.cardToSelect3Pos, selectedDestination }
	};

	// endregion

	protected final SingleGameCards cards;
	private int cardsToMove;

	private final Integer[] cardsToSelectIndices = new Integer[SingleGameCards.CardToSelectCount];

	public SingleGameCardDealer() {
		super();
		this.cards = new SingleGameCards();
	}

	@Override
	public void deal() {
		for(int i = 0; i < deck.length; i++) {
			deck[i].close();
			deck[i].deselect();
		}
		if (cards.isEmpty())
			dealAndMoveNewCards();
		else
			beginMoveOldCards();
	}

	@Override
	public void abortDeal() {
		for (int i = 0; i < cards.getLength(); i++) {
			if (cards.get(i) != null)
				cards.get(i).stopEffects();
		}
	}
	
	private void dealAndMoveNewCards() {
		dealNewCards();
		beginMoveNewCards();
	}

	private void beginMoveNewCards() {
		beginMove(newCardsMoveEndListener);
	}

	private void beginMoveOldCards() {
		beginMove(oldCardsMoveEndListener);
	}

	private void beginMove(IEffectEndListener endListener) {
		int start = 0;
		int end = 1;

		if (endListener == newCardsMoveEndListener) {
			start = 1;
			end = 0;
		}

		cardsToMove = cards.getLength();

		for (int i = 0; i < cardsToMove; i++) {
			cards.get(i).getLocation().set(routes[i][start]);
			cards.get(i).moveTo(endListener, routes[i][end], moveDuration);
		}
	}

	private void dealNewCards() {
		// select two cards
		int c1 = Utils.randInt(deck.length);
		int c2 = Utils.randInt(deck.length);
		while (c1 == c2)
			c2 = Utils.randInt(deck.length);

		// third card that makes an ichigu with the first two
		int c3 = getCompletingCardIndex(deck[c1].getAttributes(), deck[c2].getAttributes());

		// other two cards to select
		int c4 = Utils.randInt(deck.length);
		int c5 = Utils.randInt(deck.length);
		while (c4 == c1 || c4 == c2 || c4 == c3)
			c4 = Utils.randInt(deck.length);
		while (c5 == c1 || c5 == c2 || c5 == c3 || c5 == c4)
			c5 = Utils.randInt(deck.length);

		// [BugFix] Yeni katlarin icinde onceki dagitilan kartlardan
		// biri tekrar gelirse move effect siciyor.
		// onEffectEnd'de card.moveTo diyip moveEffect'e start diyoruz
		// Ama onEffectEnd'den sonra moveEffect.stop cagrildigi icin kart
		// hareket etmiyordu. simdilik boyle bir cozum uyduruldu
		// TODO: Daha akilli bir seyler yapilabilir
		if (!cards.isEmpty()) {
			// Yeni dagitilan kartlardan en az biri, bir onceki kartlarla çakışıyorsa bir daha dagit.
			for (int i = 0; i < cards.getLength(); i++) {
				Card card = cards.get(i);
				if (card.equals(deck[c1]) || card.equals(deck[c2]) ||
					card.equals(deck[c3]) || card.equals(deck[c4]) || card.equals(deck[c5])) {
					dealNewCards();
					return;
				}
			}
		}

		// shuffle cards to select
		cardsToSelectIndices[0] = c3;
		cardsToSelectIndices[1] = c4;
		cardsToSelectIndices[2] = c5;

		Utils.shuffle(cardsToSelectIndices);

		c3 = cardsToSelectIndices[0];
		c4 = cardsToSelectIndices[1];
		c5 = cardsToSelectIndices[2];

		// set cards
		cards.setCardsOnTable(deck[c1], deck[c2]);
		cards.setCardsToSelect(deck[c3], deck[c4], deck[c5]);

//		// open all cards
//		deck[c1].open();
//		deck[c2].open();
//		deck[c3].open();
//		deck[c4].open();
//		deck[c5].open();

		// put cards onto origin
		deck[c1].getLocation().set(routes[0][1]);
		deck[c2].getLocation().set(routes[1][1]);
		deck[c3].getLocation().set(routes[2][1]);
		deck[c4].getLocation().set(routes[3][1]);
		deck[c5].getLocation().set(routes[4][1]);
	}

	private int getCompletingCardIndex(CardAttributes a1, CardAttributes a2) {
		int color = CardAttributes.getCompleting(a1.getColor(), a2.getColor());
		int shape = CardAttributes.getCompleting(a1.getShape(), a2.getShape());
		int count = CardAttributes.getCompleting(a1.getCount(), a2.getCount());
		int pattern = CardAttributes.getCompleting(a1.getPattern(), a2.getPattern());
		return findCardIndex(color, shape, count, pattern);
	}

	private int findCardIndex(int color, int shape, int count, int pattern) {
		for (int i = 0; i < deck.length; i++) {
			if (deck[i].getAttributes().equals(color, shape, count, pattern))
				return i;
		}
		return -1;
	}

	private void onOldCardMoveEnd(Card card) {
		if (--cardsToMove == 0)
			dealAndMoveNewCards();
	}

	private void onNewCardMoveEnd(Card card) {
		if (--cardsToMove == 0) {
			for(int i = 0; i < deck.length; i++) {
				deck[i].open();
			}
			notifyDealEnd();
		}
			
	}

	private final IEffectEndListener oldCardsMoveEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			onOldCardMoveEnd((Card) obj);
			return true;
		}
	};

	private final IEffectEndListener newCardsMoveEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			onNewCardMoveEnd((Card) obj);
			return true;
		}
	};
	
	@Override
	public void activateCards() {
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++)
			cards.getCardsToSelect(i).activate();
	}

	@Override
	public void deactivateCards() {
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++) {
			if (cards.getCardsToSelect(i) != null)
				cards.getCardsToSelect(i).deactivate();
		}
	}

	public void updateCardLocations() {
		for (int i = 0; i < cards.getLength(); i++)
			cards.get(i).getLocation().set(R.learningModeScreen.layout.positions[i]);
	}

	@Override
	public void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			if (allCards[i] != null)
				allCards[i].draw();
	}

	public void openCloseCards(boolean open) {
		for (int i = 0; i < SingleGameCards.TotalCardCount; i++) {
			if (open)
				cards.get(i).open();
			else
				cards.get(i).close();
		}
	}

	public void emptyCards() {
		cards.empty();
	}

	@Override
	public int getScore() {
		return cards.checkScore();
	}

	@Override
	public void deselectCards() {
		cards.deselectCards();
	}

	@Override
	public void start() {
		
	}

	@Override
	public void end() {
		abortDeal();
		deactivateCards();
		cards.empty();
	}

	@Override
	public void reset() {
		resetDeck();
	}

	@Override
	public Card[] getCardsForHints() {
		Card[] hintCards = new Card[3];
		hintCards[0] = cards.get(0);
		hintCards[1] = cards.get(1);
		
		for (int i = 0; i < SingleGameCards.CardToSelectCount; i++) {
			if (Card.isIchigu(hintCards[0], hintCards[1], cards.getCardsToSelect(i))) {
				hintCards[2] = cards.getCardsToSelect(i);
				break;
			}
		}
		return hintCards;
	}

	@Override
	public boolean isIchiguAttempted() {
		return cards.getSelected() != null;
	}
}