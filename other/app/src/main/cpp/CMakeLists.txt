cmake_minimum_required(VERSION 3.10.2)
project("other")

#设置so库路径
set(my_lib_path ${CMAKE_SOURCE_DIR}/../../../libs)
add_library(other SHARED IMPORTED)
set_target_properties(other PROPERTIES IMPORTED_LOCATION ${my_lib_path}/${ANDROID_ABI}/libother.so)
include_directories(${CMAKE_SOURCE_DIR}/include/)

add_library(link-other SHARED native-lib.cpp)
find_library(log-lib log)
target_link_libraries(link-other other ${log-lib})