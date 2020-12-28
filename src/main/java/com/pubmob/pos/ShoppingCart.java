package com.pubmob.pos;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShoppingCart {
    private final Map<String, Integer> inventory;
    private final List<Integer> prices;

    public ShoppingCart(Map<String, Integer> inventory) {
        this.inventory = inventory;
        this.prices = new ArrayList<>();
    }

    private static String formatPrice(int cents) {
        return "$%.2f".formatted(cents / 100d);
    }

    String getTotal() {
        String formatPrice = formatPrice(prices.stream().reduce(0, Integer::sum));
        this.prices.clear();
        return formatPrice;
    }

    public String handleBarcode(String barcode) {
        Optional<Integer> opPrice = Optional.ofNullable(inventory.get(barcode));

        opPrice.ifPresent(price -> prices.add(price));

        return opPrice
                .map(ShoppingCart::formatPrice)
                .orElse("Barcode not found: %s.".formatted(barcode));
    }
}