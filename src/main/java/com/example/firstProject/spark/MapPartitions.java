package com.example.firstProject.spark;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapPartitions implements FlatMapFunction<Iterator<ConsumerRecord<String, String>>, String> {
    @Override
    public Iterator<String> call(Iterator<ConsumerRecord<String, String>> rowIterator) {
        List<String> wRowList = new ArrayList();
        while (rowIterator.hasNext()) {
            ConsumerRecord<String, String> wRow = rowIterator.next();
            wRowList.add(wRow.value());
        }
        return wRowList.iterator();
    }
}
