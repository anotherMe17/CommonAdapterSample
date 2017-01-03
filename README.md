# CommonAdapterSample
RecycleView的万能适配器
##目录  
* [版本](#版本)
  * [V1.1.0](#V1.1.0)
* [导入项目](#导入项目)  
  * [Maven](##Maven)
  * [Gradle](##Gradle)
  * [lvy](##lvy)
* [Thanks](#thanks)
* [Copyright](#copyright)

##版本
###V1.1.0
使用的Recyclerview版本,使用时请导入
`provided 'com.android.support:recyclerview-v7:23.2.0'`  
- [x] 创建时可使用构造者模式  
    

```java
RecyclerViewAdapter<NormalModel> mAdapter;      
RecyclerView mDataRv;
 mAdapter = new RecyclerViewAdapter.Builder<NormalModel>(mDataRv)
               	.addDelegate(new NormalDelegate())//添加条目
                .addDelegate(new TextViewDelegate())
                .addHeaderView(new TextView(this))//添加头部View
                .addFooterView(new TextView(this))//添加底部View
                .setOnRVItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public void onRvItemClick(ViewGroup parent, View itemView, int position) {
                        //
                    }
                })
                .setOnRVItemLongClickListener(new OnRvItemLongClickListener() {
                    @Override
                    public boolean onRvItemLongClick(ViewGroup parent, View itemView, int position) {
                        //
                        return false;
                    }
                })
                .setOnItemChildClickListener(new OnRvItemChildClickListener() {
                    @Override
                    public void onRvItemChildClick(ViewGroup parent, View childView, int position) {
                        //
                    }
                })
                .setOnItemChildLongClickListener(new OnRvItemChildLongClickListener() {
                    @Override
                    public boolean onRvItemChildLongClick(ViewGroup parent, View childView, int position) {
                       	//
                        return false;
                    }
                })
                .setOnItemChildCheckedChangeListener(new OnRvItemChildCheckedChangeListener() {
                    @Override
                    public void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked) {
                        //
                    }
                })
				.setOnRVItemChildTouchListener(new OnRvItemChildTouchListener() {
                    @Override
                    public boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event) {
                        //
                        return false;
                    }
                })
				.attatchView(mDataRv);
```
##导入项目
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
```groovy
	compile 'com.anotherme17:common-RvAdapter:1.1.0'
```
###lvy
```groovy
	<dependency org='com.anotherme17' name='common-RvAdapter' rev='1.1.0'>
  	 <artifact name='common-RvAdapter' ext='pom' ></artifact>
	</dependency>
```
<b id="thanks"/>
##Thanks

<b id="copyright"/>
##Copyright
```groovy   
	Copyright {2017} {anotherMe17}

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
