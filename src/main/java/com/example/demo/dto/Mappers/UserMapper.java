//
//package com.example.demo.dto.Mappers;
//import com.example.demo.dto.UserDto;
//import com.example.demo.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//@Mapper
//public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(source = "orders", target = "orders")
//    UserDto mapEntityToDto(User entity);
//
//    @Mapping(source = "orders", target = "orders")
//    User mapDtoToEntity(UserDto dto);
//}