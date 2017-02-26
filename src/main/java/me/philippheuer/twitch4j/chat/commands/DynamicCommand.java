package me.philippheuer.twitch4j.chat.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.ChannelMessageEvent;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class DynamicCommand extends Command {
	/**
	 * Return value for echo-commands
	 */
	private String commandReturnText = "";

	/**
	 * Initalize Command
	 */
	public DynamicCommand(String commandName, CommandPermission commandPermission, String commandText) {
		super();

		// Command Configuration
		setCommand(commandName);
		setCommandAliases(new String[]{});
		setCategory("dynamic");
		setDescription("Dynamic Command");
		getRequiredPermissions().add(commandPermission);
		setUsageExample("");
		setCommandReturnText(commandText);
	}

	/**
	 * executeCommand Logic
	 */
	@Override
	public void executeCommand(ChannelMessageEvent messageEvent) {
		super.executeCommand(messageEvent);

		// Prepare Response
		String response = commandReturnText;

		// Send Response
		getTwitchClient().getIrcClient().sendMessage(messageEvent.getChannel().getName(), response);
	}
}