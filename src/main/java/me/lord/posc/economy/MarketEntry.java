package me.lord.posc.economy;

import me.lord.posc.economy.offer.BuyOffer;
import me.lord.posc.economy.offer.SellOffer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class MarketEntry implements Serializable {
	@Serial
	private static final long serialVersionUID = 5839398973139673339L;

	private final ArrayList<BuyOffer> buyOffers = new ArrayList<>();
	private final ArrayList<SellOffer> sellOffers = new ArrayList<>();

	public ArrayList<BuyOffer> getBuyOffers() {
		return buyOffers;
	}

	public ArrayList<SellOffer> getSellOffers() {
		return sellOffers;
	}
}
