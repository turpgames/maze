package com;

public interface ICardDealerListener extends ICardListener {
	void onDealEnded();

	void onDeckFinished();
	
	void onIchiguFound();

	void onInvalidIchiguSelected();

}
