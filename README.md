# auto-codegen
MyBatis 前置作業有那麼一點點多
- Java version 17
- Spring Boot version 2.7.5

### stage 0
1. 自動產生相關`.class`與`.xml`
2. 用`.properties`客製化專案路徑

### stage 1
1. 加入`picocli`指令化
2. 指令控制是否預設使用`Lombok`套件
3. 加入建立`.properties`的邏輯

### stage 2
1. 指定參數判斷輸出為`.java` or`.kt`
