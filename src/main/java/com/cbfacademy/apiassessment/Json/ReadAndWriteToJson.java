package com.cbfacademy.apiassessment.Json;

import com.cbfacademy.apiassessment.orderEntry.OrderEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadAndWriteToJson {

    private final Gson gson = new Gson();

    public Collection<OrderEntry> readJsonFile(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File not found");
        }
        try (FileReader reader = new FileReader(file)) {

            TypeToken<Collection<OrderEntry>> entryType = new TypeToken<>() {
            };

            return gson.fromJson(reader, entryType);
        }
    }

    public void writeToJsonFile(OrderEntry reqBody, File file) throws IOException {
        Collection<OrderEntry> orderEntries = readJsonFile(file);
        orderEntries.add(reqBody);

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(orderEntries, writer);
        }

    }

    public Collection<OrderEntry> readJsonObjById(UUID id, File file) throws IOException {
        Collection<OrderEntry> data = readJsonFile(file);
        return data.stream()
                .filter(item -> item.getOrder_id().equals(id))
                .collect(Collectors.toList());
    }

    public void updateJsonObjById(UUID id, OrderEntry reqBody, File file) throws IOException {

        Collection<OrderEntry> orderEntries = readJsonFile(file);

        // Find and update the entry with the matching order_id
        for (OrderEntry item : orderEntries) {
            if (item.getOrder_id().equals(id)) {
                item.setSecurity_symbol(reqBody.getSecurity_symbol());
                item.setQuantity(reqBody.getQuantity());
                item.setOrderSide(reqBody.getOrderSide());
                item.setPrice(reqBody.getPrice());
                item.setStatus(reqBody.getStatus());
                item.setTimestamp(reqBody.getTimestamp());
            }
        }

        try (Writer writer = new FileWriter(file)) {
            // Write the entire collection (with updated data) back to the file
            gson.toJson(orderEntries, writer);
        }
    }

    public void deleteJsonObjById(UUID id, File file) throws IOException {

        Collection<OrderEntry> orderEntries = readJsonFile(file);

        List<OrderEntry> updateOrderEntries = orderEntries.stream()
                .filter(entry -> !entry.getOrder_id().equals(id))
                .collect(Collectors.toList());

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(updateOrderEntries, writer);
        }

    }


}
