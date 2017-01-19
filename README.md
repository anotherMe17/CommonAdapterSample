 :v: CommonAdapterSample :v:
=============
RecycleView的万能适配器
##目录  
* [TODO](#todo)
* [版本](#版本)
  * [V1.1.0](#v110)
* [导入项目](#导入项目)  
  * [Maven](#maven)
  * [Gradle](#gradle)
  * [lvy](#lvy)
* [使用方法](#使用方法)
	
* [Thanks](#thanks)
* [Copyright](#copyright)

<b id="todo"/>
##TODO
- [ ] 增加动画效果
- [ ] 增加EmptyView
- [ ] 增加下拉刷新上拉加载
- [ ] 增加数据绑定

##版本
<b id="v110"/>
###V1.1.0
使用的Recyclerview版本,使用时请导入
`provided 'com.android.support:recyclerview-v7:23.2.0'`  
- [x] 创建时可使用构造者模式  
    
##使用方法
详细的使用方法请参考demo  

##导入项目
<b id="maven"/>
###Maven
```groovy
    <dependency>  
    <groupId>com.anotherme17</groupId>  
	<artifactId>common-RvAdapter</artifactId>  
	<version>1.1.0</version>  
	<type>pom</type>  
	</dependency>
```
###Gradle
<b id="gradle"/>
```groovy
	compile 'com.anotherme17:common-RvAdapter:1.1.0'
```
###lvy
<b id="lvy"/>
```groovy
	<dependency org='com.anotherme17' name='common-RvAdapter' rev='1.1.0'>
  	 <artifact name='common-RvAdapter' ext='pom' ></artifact>
	</dependency>
```
<b id="thanks"/>
##Thanks
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper "BaseRecyclerViewAdapterHelper")

<b id="copyright"/>
##Copyright
```
	Copyright {2017} 李仁豪

   	Licensed under the Apache License, Version 2.0 (the "License");
   	you may not use this file except in compliance with the License.
   	You may obtain a copy of the License at

   	http://www.apache.org/licenses/LICENSE-2.0

   	Unless required by applicable law or agreed to in writing, software
   	distributed under the License is distributed on an "AS IS" BASIS,
   	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   	See the License for the specific language governing permissions and
   	limitations under the License.
```
