# Jalegram - A Java Telegram Bot Library
![Maven Central](https://img.shields.io/maven-central/v/io.github.init-io/jalegram.svg?label=Maven%20Central)
![GitHub Release](https://img.shields.io/github/v/release/init-io/jalegram?label=release)
[![License](https://img.shields.io/github/license/init-io/jalegram)](LICENSE)
![downloads](https://img.shields.io/badge/downloads-1k%2Fmonth-brightgreen)

Jalegram is a lightweight, user-friendly Java library designed to simplify the development of Telegram bots. It provides a straightforward interface to interact with the Telegram Bot API, offering a wide range of methods to handle messages, media, chat management, and more—all with minimal boilerplate code compared to other libraries.

## Features
- Easy-to-use methods for sending messages, photos, videos, and other media.
- Comprehensive chat and user information retrieval.
- Support for keyboards, inline keyboards, and polls.
- Chat management tools like banning, promoting, and restricting users.
- Built-in JSON parsing for seamless interaction with Telegram's API responses.
- Extensible and well-documented codebase.

## Prerequisites
- Java 8 or higher
- Maven or Gradle (for dependency management)
- A Telegram Bot Token (obtained from [BotFather](https://t.me/BotFather))

## Installation

### Maven
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>io.github.initio</groupId>
    <artifactId>jalegram</artifactId>
    <version>1.0.0</version> <!-- Replace with the actual version -->
</dependency>
```

### Gradle
Add this line to your `build.gradle`:
```gradle
implementation 'io.github.initio:jalegram:1.0.0' // Replace with the actual version
```

*Note:* If Jalegram is not yet published to a Maven repository, you'll need to build it locally from the source and install it to your local Maven repository using:
```bash
mvn clean install
```

## Getting Started

1. **Create a Telegram Bot**: Talk to [BotFather](https://t.me/BotFather) on Telegram to create a bot and get your bot token.
2. **Initialize Jalegram**: Use your bot token to instantiate the `Jalegram` class.

```java
import io.github.initio.jalegram.Jalegram;

public class MyBot {
    public static void main(String[] args) {
        String botToken = "YOUR_BOT_TOKEN_HERE";
        Jalegram bot = new Jalegram(botToken);

        try {
            // Example: Send a message
            String response = bot.sendMessage("CHAT_ID", "Hello from Jalegram!");
            System.out.println(response);

            // Example: Get updates
            String updates = bot.getUpdates(0);
            System.out.println("Chat ID: " + bot.getChatId(updates));
            System.out.println("Message Text: " + bot.getText(updates));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Replace `"YOUR_BOT_TOKEN_HERE"` with your actual bot token and `"CHAT_ID"` with the target chat ID.

## Available Methods

### Core Messaging
- `sendMessage(chatId, text)`: Send a text message.
- `sendReplyMessage(chatId, text, replyToMessageId)`: Reply to a specific message.
- `editMessage(chatId, messageId, newText)`: Edit an existing message.
- `deleteMessage(chatId, messageId)`: Delete a message.
- `forwardMessage(chatId, fromChatId, messageId)`: Forward a message.

### Media Sending
- `sendPhoto(chatId, photo, caption)`: Send a photo.
- `sendVideo(chatId, video, caption)`: Send a video.
- `sendAudio(chatId, audio, caption)`: Send an audio file.
- `sendFile(chatId, file, caption)`: Send any document/file.
- `sendSticker(chatId, sticker)`: Send a sticker.
- `sendVoice(chatId, voice, caption)`: Send a voice message.
- `sendAnimation(chatId, animation, caption)`: Send an animation (GIF).

### Updates and Information Retrieval
- `getUpdates(offset)`: Fetch bot updates.
- `getText(jsonResponse)`: Extract message text from updates.
- `getChatId(jsonResponse)`: Get the chat ID from updates.
- `getUserId(jsonResponse)`: Get the user ID from updates.
- `getUsername(jsonResponse)`: Get the user's username.
- `getUserFirstName(jsonResponse)`: Get the user's first name.
- `getUserLastName(jsonResponse)`: Get the user's last name.
- `getChatType(jsonResponse)`: Get the chat type (e.g., private, group).
- `getMessageId(jsonResponse)`: Get the message ID.
- `getUpdateId(jsonResponse)`: Get the update ID.
- `getDate(jsonResponse)`: Get the message timestamp.

### Keyboards
- `sendKeyboard(chatId, text, buttons)`: Send a custom keyboard.
- `sendInlineKeyboard(chatId, text, buttons, callbackData)`: Send an inline keyboard with callback data.

### Chat Management
- `banChatMember(chatId, userId)`: Ban a user from a chat.
- `unbanChatMember(chatId, userId)`: Unban a user.
- `restrictChatMember(chatId, userId, canSendMessages)`: Restrict a user's permissions.
- `promoteChatMember(chatId, userId)`: Promote a user to admin.
- `setChatTitle(chatId, title)`: Set the chat title.
- `setChatPhoto(chatId, photo)`: Set the chat photo.
- `leaveChat(chatId)`: Make the bot leave a chat.
- `pinChatMessage(chatId, messageId)`: Pin a message.
- `unpinChatMessage(chatId, messageId)`: Unpin a message (or all if `messageId` is null).

### Miscellaneous
- `sendLocation(chatId, latitude, longitude)`: Send a location.
- `sendPoll(chatId, question, options)`: Send a poll.
- `sendDice(chatId)`: Send a dice animation.
- `getChatMember(chatId, userId)`: Get chat member info.
- `getChatAdministrators(chatId)`: Get list of chat admins.
- `getChatMembersCount(chatId)`: Get the number of chat members.
- `getFile(fileId)`: Get file details by file ID.
- `setMyCommands(commands)`: Set custom bot commands.

## Example: Simple Echo Bot

```java
import io.github.initio.jalegram.Jalegram;
import java.io.IOException;

public class EchoBot {
    public static void main(String[] args) throws IOException {
        Jalegram bot = new Jalegram("YOUR_BOT_TOKEN_HERE");
        int offset = 0;

        while (true) {
            String updates = bot.getUpdates(offset);
            long updateId = bot.getUpdateId(updates);
            if (updateId != -1) {
                offset = (int) updateId + 1;
                String text = bot.getText(updates);
                long chatId = bot.getChatId(updates);
                if (!text.isEmpty()) {
                    bot.sendMessage(String.valueOf(chatId), "You said: " + text);
                }
            }
            try {
                Thread.sleep(1000); // Polling delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Contributing
Contributions are welcome! Feel free to submit a pull request or open an issue on the [GitHub repository](https://github.com/yourusername/jalegram) (replace with your actual repo link).

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements
- Built with [OkHttp](https://square.github.io/okhttp/) for HTTP requests.
- Inspired by the Telegram Bot API community.
