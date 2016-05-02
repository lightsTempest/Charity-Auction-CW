package org.bogdanbuduroiu.auction.model.comms.message;

import org.bogdanbuduroiu.auction.model.Bid;
import org.bogdanbuduroiu.auction.model.Item;
import org.bogdanbuduroiu.auction.model.User;

import java.io.Serializable;

/**
 * Created by bogdanbuduroiu on 23.04.16.
 */
public class NewBidRequest extends Message implements Serializable {

    private Bid bid;
    private Item item;

    public NewBidRequest(Item item, Bid bid) {
        super(bid.getUser(), MessageType.NEW_BID_REQUEST);
        this.bid = bid;
        this.item = item;
    }

    public Bid getBid() {
        return bid;
    }

    public Item getItem() {
        return item;
    }
}
