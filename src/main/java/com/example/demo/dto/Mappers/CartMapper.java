////this method is better than creating them manuallyit's strongly recommended to use MapStruct or a similar mapping library to handle DTO-to-entity and entity-to-DTO mapping in your Spring Boot application, as it simplifies the mapping process and produces more efficient and maintainable code.
//package com.example.demo.dto.Mappers;
//import com.example.demo.dto.CartDto;
//import com.example.demo.model.Cart;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface CartMapper {
//    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
//
//    @Mapping(source = "cartItemList", target = "cartItemList")
//    CartDto mapEntityToDto(Cart entity);
//
//    @Mapping(source = "cartItemList", target = "cartItemList")
//    Cart mapDtoToEntity(CartDto dto);
//}
