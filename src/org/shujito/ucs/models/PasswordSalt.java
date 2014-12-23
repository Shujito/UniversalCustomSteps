package org.shujito.ucs.models;

import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class PasswordSalt
{
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	@SerializedName(value = PASSWORD)
	public String password;
	@SerializedName(value = SALT)
	public String salt;
	
	@Override
	public boolean equals(Object obj)
	{
		try
		{
			if (this == obj)
				return true;
			if (!(obj instanceof PasswordSalt))
				return false;
			PasswordSalt cast = (PasswordSalt) obj;
			byte[] thisPassword = Base64.decode(this.password);
			byte[] castPassword = Base64.decode(cast.password);
			int diff = thisPassword.length ^ castPassword.length;
			for (int idx = 0; idx < thisPassword.length && idx < castPassword.length; idx++)
			{
				diff |= thisPassword[idx] ^ castPassword[idx];
			}
			return diff == 0;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
}
