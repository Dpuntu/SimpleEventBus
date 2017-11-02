# SimpleEventBus

### 一个[EventBus](https://github.com/greenrobot/EventBus)的简约版本

#### 请参考 [EventBus](https://github.com/greenrobot/EventBus)

##### 使用规范

* 绑定
```  
@Override
    protected void onCreate(Bundle savedInstanceState) {
       ...
       SimpleEventBus.getDefault().register(this);
       ...
    }
``` 

* 解绑
```  
@Override
    protected void onDestroy() {
        ...
        SimpleEventBus.getDefault().unRegister(this);
    }
``` 

* 添加接受信息的方法体</br>
###### 需要注意的两个地方：</br>
######    1.收信息的方法必须添加EventThread注解</br>
######    2.方法必须是public
      
```  
@EventThread
public void mainActivity(String str) {
        Log.e("bus_test", "OtherActivity -> " + str);
    }
``` 

* 发送消息
``` 
   SimpleEventBus.getDefault().post("test");
``` 

### 当然还有很多不规范的地方，只是作为学习EventBus的工具，后面出专门的博客讲解EventBus
