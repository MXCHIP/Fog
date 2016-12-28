# Fog2.0Demo

请注释settings.gradle中的这部分
```js
include ':Fog2_container'
project(':Fog2_container').projectDir = new File('../../../../../develop/androids/Fog2_container/')
include ':Fog2_container:fog2_sdk'
```

请注释app/build.gradle中的这部分
```js
compile project(':Fog2_container:fog2_sdk')
```

添加依赖，添加依赖，添加依赖
```js
dependencies {
    compile 'io.fogcloud.sdk:fog2_sdk:0.1.1'
}
```
