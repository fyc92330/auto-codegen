# auto-codegen
MyBatis 前置作業有那麼一點點多
- Java version 17
- Spring Boot version 2.7.5

### stage 0
1. `創建者模式`: 自動產生相關`.class`與`.xml`
2. 用`.properties`客製化專案路徑

### stage 1
1. 增加`佇立者模式`: 客製化建立`.properties`

### stage 2
1. `佇立者模式`增加重置指令
2. `創建者模式`控制是否預設使用`Lombok`套件

### stage 3
1. 指定參數判斷輸出為`.java` or`.kt`
