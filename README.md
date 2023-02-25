# ecommerce-SSM
## 簡介
使用Spring + SpringMVC + Mybatis搭建的電商前台
## 使用技術
* Spring
* SpringMVC
* Mybatis
* MySQL
* Jakarta Mail API
* PageHelper
* C3P0
* JSP
* jQuery
* AJAX
## 版本資訊
* 數據庫：MySQL 8.0.32
* JDK版本：JDK 17
* Tomcat版本：Tomcat 10.0.27
## 頁面及功能介紹
### 一、 首頁
* 登入前導行列只有註冊跟登入，登入後導行列有購物車、訂單與登出
![index](https://user-images.githubusercontent.com/65711157/221377489-6f1fea15-aeea-4e02-81ba-03b1e86227d1.jpg)
* 點擊頁碼會跳到指定的頁碼，若當前為第一頁則不會上一頁，反之亦然
![index_login](https://user-images.githubusercontent.com/65711157/221377664-6f7b764a-ce58-4040-a60c-c94bf0392557.jpg)
### 二、登入頁面
* 記住我功能，若勾選會自動填入電子信箱與密碼
![login](https://user-images.githubusercontent.com/65711157/221377849-07178b80-95ff-4878-be7c-92fada267b73.jpg)
* 登入失敗會出現提示，並保留先前輸入的電子信箱
![login_wrong](https://user-images.githubusercontent.com/65711157/221377972-f4004f75-876b-411b-a97f-5796f81ec22c.jpg)
### 三、商品頁面
* 可以選擇購買數量
![item](https://user-images.githubusercontent.com/65711157/221378156-30187e00-6aa2-407d-961f-6496ba64d48d.jpg)
* 下架的商品只顯示商品資訊，無法加入購物車
![item_closed](https://user-images.githubusercontent.com/65711157/221378171-2106fdb4-00b7-4016-ba05-60853408a422.jpg)
### 四、購物車
* 顯示商品數量及總金額
* 可修改商品數量，金額也會隨之變動
* 購物車含有下架商品會導致結帳失敗，有相應的提示訊息
![cart](https://user-images.githubusercontent.com/65711157/221378265-59514bea-971d-4863-bd0d-f110394c9ee8.jpg)
### 四、歷史訂單
* 顯示訂單基本訊息
* 點擊訂單編號可顯示訂單詳情
* 若訂單還未出貨，則可以取消訂單
![order](https://user-images.githubusercontent.com/65711157/221378368-f646eee3-6c74-481e-a9d9-cb15cfc495d4.jpg)
### 五、訂單詳情
* 顯示訂單詳情，包含商品、數量、金額
![order_spe](https://user-images.githubusercontent.com/65711157/221378471-7cec71cb-81f7-4310-a719-9c6dde964033.jpg)

