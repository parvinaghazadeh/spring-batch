package com.parvin.springbatch.batch;

import com.parvin.springbatch.model.DataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class BatchItemProcessor implements ItemProcessor<DataModel, DataModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchItemProcessor.class);

    @Override
    public DataModel process(final DataModel dataModel) {
        String brand = dataModel.getBrand().toUpperCase();
        String origin = dataModel.getOrigin().toUpperCase();
        String chracteristics = dataModel.getCharacteristics().toUpperCase();

        DataModel transformedCoffee = new DataModel(brand, origin, chracteristics);
        LOGGER.info("Converting ( {} ) into ( {} )", dataModel, transformedCoffee);

        return transformedCoffee;
    }

}
