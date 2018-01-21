
# Framework

- backend: SpringBoot + JPA + mybatis + mysql
- frontend: webpack + vuejs + element ui

# How to run

## front
```
cd front
npm install
npm run dev   # develop mod
npm run build # build the project
```

## backend
```
cd backend
mvn eclipse:eclipse
mvn spring-boot:run
```

## update
### 2017.12.23
1. tag 支持多种顔色以及自选颜色
2. 修复tag filter 的bug
> bug 新加link后默认加载全部tag，不是选中的tag；搜索的时候也有这个问题好像

3. 记录生成过的 `text resource`,并支持搜索、修改和重新生成。
4. text resource 支持转义字符，如`"` `\`

### 2018.01.21
1. 支持服务端搜索text resource
2. 支持带”的csv格式的text resource导入和生成。

# Relate Links
- [vue-admin](https://github.com/taylorchen709/vue-admin)
- [SpringBoot-MyBatis](https://github.com/ShawnyXiao/SpringBoot-MyBatis)
- [element](https://github.com/ElemeFE/element)
