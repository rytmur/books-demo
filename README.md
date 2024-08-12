# books-demo

<div id="top"></div>

## 目次

1. [プロジェクトについて](#プロジェクトについて)
2. [環境](#環境)
3. [開発環境構築](#開発環境構築)
4. [トラブルシューティング](#トラブルシューティング)

## プロジェクトについて

書籍管理システム

<p style="text-align: end;">(<a href="#top">トップへ</a>)</p>

## 環境

<!-- 言語、フレームワーク、ミドルウェア、インフラの一覧とバージョンを記載 -->

| 言語・フレームワーク  | バージョン  |
|-------------|--------|
| Java        | 21     |
| Spring Boot | 3.3.2  |
| PostgreSQL  | 16.3.1 |

その他のパッケージのバージョンは build.gradle.kts を参照してください

<p style="text-align: end;">(<a href="#top">トップへ</a>)</p>

## 開発環境構築

### PostgreSQL (Docker)

postgres:latestのDockerイメージをインストールする。
```shell
docker pull postgres:latest
```

### Flyway Migrate

プロジェクトをビルドし、Flywayによるデータベースのマイグレーションを行う。
```shell
./gradlew flywayMigrate
```

### jOOQ

postgresのDockerコンテナを起動後、jOOQの自動コード生成を行う。
```shell
./gradlew jooqCodegen
```

### 動作確認

アプリケーションを起動する。  
http://127.0.0.1:8080/api/v1/books/search がfetchできるか確認する。  
200 OKのHTTPレスポンスが返却されたら成功。

<p style="text-align: end;">(<a href="#top">トップへ</a>)</p>

## トラブルシューティング

### error during connect: Get "http://%2F%2F.%2Fpipe%2FdockerDesktopLinuxEngine/v1.46/version": open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified.

Docker Desktop が起動できていない可能性があります。  
Docker Desktopを起動してください。

<p style="text-align: end;">(<a href="#top">トップへ</a>)</p>
