# FS
***FSDemo***

repositories 中引入

maven { url  "https://dl.bintray.com/sar01/maven" }

项目导入，需要在gradle下引入

implementation 'com.sar:fs:0.0.2'

//annotations 注解

annotationProcessor 'org.androidannotations:androidannotations:4.6.0'

且需要在
 **defaultConfig**

 中支持

 multiDexEnabled true