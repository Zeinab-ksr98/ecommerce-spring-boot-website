
package com.example.demo.dto.Mappers;
import com.example.demo.dto.OnlineOrdersDto;
import com.example.demo.model.OnlineOrders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface OnlineOrdersMapper {
    OnlineOrdersMapper INSTANCE = Mappers.getMapper(OnlineOrdersMapper.class);

    @Mapping(source = "productList", target = "productList")
    OnlineOrdersDto mapEntityToDto(OnlineOrders entity);

    @Mapping(source = "productList", target = "productList")
    OnlineOrders mapDtoToEntity(OnlineOrdersDto dto);
}