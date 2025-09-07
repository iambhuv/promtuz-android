cmake_minimum_required(VERSION 3.22.1)

project(CoreBridge LANGUAGES CXX)

add_library(lib_core SHARED IMPORTED)
set_target_properties(lib_core
        PROPERTIES
        IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libcore.so
)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}")


add_library(core_bridge SHARED
        core_bridge.cpp
)
target_include_directories(core_bridge PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/include)

target_link_libraries(core_bridge
        android
        lib_core
        log
        OpenSLES
)

#
#project("core_bridge")
#
## Print API level for debugging
#message(STATUS "Detected Android API level: ${CMAKE_SYSTEM_VERSION}")
#
#add_library(core_bridge SHARED core_bridge.cpp)
#
## Add 16KB alignment flags for Android 15+ compatibility
#if (CMAKE_SYSTEM_VERSION GREATER_EQUAL 35)  # Android 15 is API level 35
#    message(STATUS "Applying 16KB alignment for API 35+")
#    target_link_options(core_bridge PRIVATE
#            -Wl,-z,max-page-size=16384
#            -Wl,-z,common-page-size=16384
#    )
#else ()
#    message(STATUS "Applying fallback alignment for API <35")
#    # Fallback for older Android versions
#    target_link_options(core_bridge PRIVATE
#            -Wl,-z,max-page-size=16384
#            -Wl,-z,common-page-size=4096
#    )
#endif ()
#
## Import the pre-built Rust library
#add_library(libcore SHARED IMPORTED)
#set_target_properties(libcore PROPERTIES
#        IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libcore.so
#)
#
## Link the Rust library to our bridge
#target_link_libraries(core_bridge libcore log)
#
## Include directories
#target_include_directories(core_bridge PRIVATE ${CMAKE_SOURCE_DIR})
