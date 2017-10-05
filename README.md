# promane
プロジェクトのタスクをマネジメントするWebアプリケーションです。  
開発に関する情報はWikiを確認してください。  
ユーザ作成画面とログイン画面以外のページへアクセスするにはログインが必要です。

## 起動方法
``` terminal
user:promane user$ ./mvnw spring-boot:run
```

## URL一覧
基本はWebブラウザのアドレスに「localhost:8080/hoge」です。  
8080ポートをすでに使用している場合はapplication.propertiesを以下のように変更してください。
``` application.properties
server.port = 8090
```

### /users
ユーザを作成することができます。  

### /loginForm
ログイン画面です。

### /h2-console
RDBMSのJDBCです。  
```
JDBC URL: jdbc:h2:file:./target/db/intern
```

### /users/userList
adminユーザはこの画面からユーザ情報の閲覧・編集を行うことができます。  
2017/10/1 現在、adminユーザを追加する方法はありません。  
h2-consoleから手動で変更してください。
```
//user idが1の場合
UPDATE USERS SET ROLE = 'ADMIN_USER' WHERE ID = 1;
```
