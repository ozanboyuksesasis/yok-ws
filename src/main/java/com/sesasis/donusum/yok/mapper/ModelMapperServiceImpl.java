package com.sesasis.donusum.yok.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Service
public class ModelMapperServiceImpl implements ModelMapperService {


    private final ModelMapper modelMapper;

    @Override
    public ModelMapper request() {
        this.modelMapper.getConfiguration().
                setMatchingStrategy(MatchingStrategies.STANDARD).setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Override
    public ModelMapper response() {
        this.modelMapper.getConfiguration().
                setMatchingStrategy(MatchingStrategies.LOOSE).setAmbiguityIgnored(true);
        return modelMapper;
    }
}
