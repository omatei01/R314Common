package ro.anzisoft.common.types;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author omatei01
 *
 */
public enum UserRoleEnum {
	ANONYMOUS(-1, "ANONYMOUS"), 	//pentru comenzi fara user dar cu apikey: gen ping, notif etc 
	REGULAR(0, "REGULAR"), 	//casier, normal
	SUPERVISOR(1, "SUPER"),	//supervizor
	ADMIN(2, "ADMIN"); 		//administrator, service

	int id;
	String name;

	private UserRoleEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static UserRoleEnum fromString(String string) {
		return Optional.ofNullable(MAP.get(string.toLowerCase()))
		        .orElseThrow(() -> new IllegalArgumentException(string));
	}

	//colectie de forma: categ.code -> de ex: "SystemService.Ping"
	private static Map<String, UserRoleEnum> MAP = Stream.of(UserRoleEnum.values()).collect(Collectors.toMap(s -> s.getName()
	        .toLowerCase(), Function.identity()));
}
