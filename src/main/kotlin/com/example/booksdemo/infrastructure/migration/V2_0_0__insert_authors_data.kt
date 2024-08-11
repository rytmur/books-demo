package db.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context

class V2_0_0__insert_authors_data : BaseJavaMigration() {
    @Throws(Exception::class)
    override fun migrate(context: Context) {
        val connection = context.connection
        for (i in 0 until 10) {
            connection.prepareStatement(
                "INSERT INTO authors (name) VALUES (?)"
            ).use { statement ->
                statement.setString(1, "著者${i + 1}")
                statement.executeUpdate()
            }

            for (j in 0 until 10) {
                connection.prepareStatement(
                    "INSERT INTO books (title, author_id) VALUES (?, ?)"
                ).use { statement ->
                    statement.setString(1, "書籍$i$j")
                    statement.setInt(2, i + 1)
                    statement.executeUpdate()
                }
            }
        }
    }
}
