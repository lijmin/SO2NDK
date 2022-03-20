# SO2NDK
NDK开发加入so动态库

## 前言
主要讲述在android中调用一个没有jni接口的so动态库。由于第三方库没有jni接口，只能通过c++调用。我们用NDK创建自己的so库，并在自己的so库中调用第三方库中的接口。

## 准备
1. 创建other.so库当作第三方的so库。
2. 创建link_other.so库来调用other.so,并提供jni接口。
3. 创建普通项目测试。 
   项目地址 github：<https://github.com/lijmin/SO2NDK>
   
<!--More-->
### 创建other.so
在android studio中创建native c++项目
File->New->New Project->Native C++
![](https://lijmin.github.io/00_blogImg/img_blog15_01.png)
在cpp目录下创建other的C++类，并添加一个add方法
```c++
#include "other.h"

int other::add(int a, int b) {
    return a+b;
}
```
在CMakeLists.txt里面加入other.cpp
![](https://lijmin.github.io/00_blogImg/img_blog15_02.png)
点击运行,在app->build->intermediates->cmake->debug->obj->x86目录下可看到生成到libother.so
将libother.so、other.h文件拷贝出来备用。

### 创建link_other.so
直接在第一个项目上面改
把libother.so加入到libs目录
创建include目录并把other.h的头文件放到里面
![](https://lijmin.github.io/00_blogImg/img_blog15_03.png)
修改CMakeList.txt，加入other.so
```
#设置so库路径
set(my_lib_path ${CMAKE_SOURCE_DIR}/../../../libs)
add_library(other SHARED IMPORTED)
set_target_properties(other PROPERTIES IMPORTED_LOCATION ${my_lib_path}/${ANDROID_ABI}/libother.so)
include_directories(${CMAKE_SOURCE_DIR}/include/)

add_library(link-other SHARED native-lib.cpp)
find_library(log-lib log)
target_link_libraries(link-other other ${log-lib})
```
创建NDK方法，来调用other.so里面到add方法。
![](https://lijmin.github.io/00_blogImg/img_blog15_04.png)
![](https://lijmin.github.io/00_blogImg/img_blog15_05.png)
同样点击运行,在app->build->intermediates->cmake->debug->obj->x86目录下可看到生成了两个so文件。
这个时候在当前项目内是无法运行的，需要把两个so拿到测试项目运行。

## 测试link-other.so
同样将两个so加入到新项目的libs
在app的build.gradle加入编译路径
```
android{
    ...
    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }
    ...
}
```
创建与link-other.so相同包名类名的方法，并加载动态库。
![](https://lijmin.github.io/00_blogImg/img_blog15_06.png)
在MainActivity里面调用native方法
![](https://lijmin.github.io/00_blogImg/img_blog15_07.png)
![](https://lijmin.github.io/00_blogImg/img_blog15_08.png)

