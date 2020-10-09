package movies.spring.data.neo4j.users;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Function;

import org.springframework.data.neo4j.repository.event.BeforeBindCallback;
import org.springframework.stereotype.Component;

/**
 * An example of a custom callback that changes an entity before it is persisted.
 */
@Component
public class UserPasswordCallback implements BeforeBindCallback<User> {

	@Override
	public User onBeforeBind(User entity) {
		entity.setHashedPassword(hashPassword(entity.getName(), entity.getPassword()));
		return entity;
	}

	/**
	 * One way to do this. A better way would be using one of Spring Security password encoders,
	 * described here https://info.michael-simons.eu/2018/01/13/spring-security-5-new-password-storage-format/
	 * But I didn't want to add all that infrastructure just yet.
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	static String hashPassword(String userName, String password) {
		try {
			var digester = MessageDigest.getInstance("SHA-256");
			var digest = digester.digest(String.format("%s:%s", userName, password)
				.getBytes(StandardCharsets.UTF_8));

			var rv = new StringBuilder();
			for (byte b : digest) {
				rv.append(Integer.toHexString((b & 0xff) + 0x100).substring(1));
			}
			return rv.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}