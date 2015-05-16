package org.shujito.ucs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public final class Database
{
	public static final String TAG = Database.class.getSimpleName();
	private static final Connection sConnection;
	//private static final List<String> sTransactionStack = new ArrayList<>();
	static
	{
		try
		{
			sConnection = DriverManager.getConnection("jdbc:sqlite:ucs.db3");
			//sConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
			//sConnection.setAutoCommit(false);
			try (Statement smt = sConnection.createStatement())
			{
				smt.executeUpdate("create table if not exists users ("
					+ "uuid text not null on conflict fail default (lower(hex(randomblob(16)))),"
					+ "created_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "updated_at integer not null on conflict ignore default (cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "deleted_at integer,"
					+ "username text not null on conflict fail unique on conflict fail,"
					+ "display_name text not null on conflict fail,"
					+ "email text not null on conflict fail unique on conflict fail,"
					+ "primary key (uuid) on conflict replace"
					+ ")");
				smt.executeUpdate("create table if not exists user_passwords ("
					+ "user_uuid text not null on conflict fail unique on conflict replace,"
					+ "password text not null on conflict fail,"
					+ "salt text not null on conflict fail,"
					+ "foreign key (user_uuid) references users(uuid),"
					+ "primary key (user_uuid) on conflict replace"
					+ ")");
				smt.executeUpdate("create table if not exists sessions ("
					+ "uuid text not null on conflict fail default (lower(hex(randomblob(16)))),"
					+ "expires_at integer not null on conflict ignore default (cast(((julianday('now','+7 days') - julianday('1970-01-01')) * 86400000) as integer)),"
					+ "user_uuid text not null on conflict ignore,"
					+ "foreign key (user_uuid) references users(uuid),"
					+ "primary key (uuid) on conflict replace"
					+ ")");
			}
			//sConnection.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
			throw new RuntimeException(e);
		}
	}
	
	public static final Statement createStatement() throws Exception
	{
		return sConnection.createStatement();
	}
	
	public static final PreparedStatement prepareStatement(String sql) throws Exception
	{
		return sConnection.prepareStatement(sql);
	}
	
	private Database()
	{
		throw new RuntimeException("do not!");
	}
}
