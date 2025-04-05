package io.github.initio.jalegram;

import java.io.File;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException; 

public class Jalegram {
    private static final String API_URL = "https://api.telegram.org/bot";
    private final String token;
    private final OkHttpClient client;

    public Jalegram(String token) {
        this.token = token;
        this.client = new OkHttpClient();
    }

    // Method for extracting the last result from the user
    private JSONObject getLastResult(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray results = response.optJSONArray("result");

            // Handle empty result case
            if (results == null || results.length() == 0) {
                return null; // Explicitly return null when no updates exist
            }

            // Process all results, not just the last one
            JSONObject lastResult = null;
            for (int i = 0; i < results.length(); i++) {
                lastResult = results.optJSONObject(i);
                // You can add any other logic here to process each result, if needed
            }

            return lastResult;
        } catch (Exception e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
        }
        return null; // Return null if an error occurs
    }

    //Sending message to the user
    public String sendMessage(String chatId, String text) throws IOException {
        String url = API_URL + token + "/sendMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("text", text);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //getting updates from user 
    public String getUpdates(int offset) throws IOException {
        String url = API_URL + token + "/getUpdates?offset=" + offset;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // getting user input from the telegram app
    public String getText(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result != null) {
            JSONObject message = result.optJSONObject("message");
            return (message != null) ? message.optString("text", "") : "";
        }
        return ""; // Return empty if no message is found
    }

    //getting the date 
    public long getDate(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return 0L; // Return 0 if no updates are available
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return 0L; // Return 0 if there's no message
        }

        return message.optLong("date", 0L);
    }

    //getting userid for some operations
    public long getUserId(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return -1;
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return -1;
        }

        JSONObject from = message.optJSONObject("from");
        if (from == null) {
            return -1;
        }

        return from.optLong("id", -1);
    }

    //Method of extracting user's first name
    public String getUserFirstName(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "Unknown";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "Unknown";
        }

        JSONObject from = message.optJSONObject("from");
        if (from == null) {
            return "Unknown";
        }

        return from.optString("first_name", "Unknown");
    }

    //Method of extracting user's last name
    public String getUserLastName(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "Unknown";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "Unknown";
        }

        JSONObject from = message.optJSONObject("from");
        if (from == null) {
            return "Unknown";
        }

        return from.optString("last_name", "Unknown");
    }

    //Method of extracting user's username 
    public String getUsername(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "null";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "null";
        }

        JSONObject from = message.optJSONObject("from");
        if (from == null) {
            return "null";
        }

        return from.optString("username", "null");
    }

    //Method of getting the chatId
    public long getChatId(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            //System.out.println("No valid result found in JSON response.");
            return -1;
        }

        JSONObject message = result.optJSONObject("message");
        JSONObject chat = (message != null) ? message.optJSONObject("chat") : null;

        if (chat == null) {
            System.out.println("No chat object found.");
            return -1;
        }

        return chat.optLong("id", -1);
    }

    //Method of extracting the chat's first name
    public String getChatFirstName(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "null";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "null";
        }

        JSONObject chat = message.optJSONObject("chat");
        if (chat == null) {
            return "null";
        }

        return chat.optString("first_name", "null");
    }

    //Method of extracting the chat's last name
    public String getChatLastName(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "null";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "null";
        }

        JSONObject chat = message.optJSONObject("chat");
        if (chat == null) {
            return "null";
        }

        return chat.optString("last_name", "null");
    }

    //Method of extracting the chat's username
    public String getChatUsername(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "null";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "null";
        }

        JSONObject chat = message.optJSONObject("chat");
        if (chat == null) {
            return "null";
        }

        return chat.optString("username", "null");
    }

    //Method of extracting the chat's type wheater it's private or public
    public String getChatType(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return "null";
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return "null";
        }

        JSONObject chat = message.optJSONObject("chat");
        if (chat == null) {
            return "null";
        }

        return chat.optString("type", "null");
    }

    //Method of extracting the messageId for some important operations
    public long getMessageId(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result == null) {
            return -1;
        }

        JSONObject message = result.optJSONObject("message");
        if (message == null) {
            return -1;
        }

        return message.optLong("message_id", -1);
    }

    //Method of extracting the updateId to reply only the latest message
    public long getUpdateId(String jsonResponse) {
        JSONObject result = getLastResult(jsonResponse);
        if (result != null) {
            return result.optLong("update_id", -1); // Safely return the update_id if available
        }
        return -1; // Return -1 if there are no updates or result is null
    }

    //Method of sending photos to the user
    public String sendPhoto(String chatId, File photo, String caption) throws IOException {
        String url = API_URL + token + "/sendPhoto";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("photo", photo.getName(),
                        RequestBody.create(photo, MediaType.parse("image/jpeg")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method of sending videos to the user
    public String sendVideo(String chatId, File video, String caption) throws IOException {
        String url = API_URL + token + "/sendVideo";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("video", video.getName(),
                        RequestBody.create(video, MediaType.parse("video/mp4")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method of sending audios to the user
    public String sendAudio(String chatId, File audio, String caption) throws IOException {
        String url = API_URL + token + "/sendAudio";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("audio", audio.getName(),
                        RequestBody.create(audio, MediaType.parse("audio/mpeg")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method of sending almost all types of file
    public String sendFile(String chatId, File file, String caption) throws IOException {
        if (!file.exists()) {
            return "Error: File does not exist.";
        }

        String url = API_URL + token + "/sendDocument";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("document", file.getName(),
                        RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method of replying on a specefic message of the user
    public String sendReplyMessage(String chatId, String text, long replyToMessageId) throws IOException {
        String url = API_URL + token + "/sendMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("text", text);
        payload.put("reply_to_message_id", replyToMessageId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method of sending keyBoard for some important tasks
    public String sendKeyboard(String chatId, String text, String[][] buttons) throws IOException {
        String url = API_URL + token + "/sendMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("text", text);

        JSONObject replyMarkup = new JSONObject();
        JSONArray keyboardArray = new JSONArray();

        for (String[] row : buttons) {
            JSONArray buttonRow = new JSONArray();
            for (String buttonText : row) {
                JSONObject button = new JSONObject();
                button.put("text", buttonText);
                buttonRow.put(button);
            }
            keyboardArray.put(buttonRow);
        }

        replyMarkup.put("keyboard", keyboardArray);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", true);

        payload.put("reply_markup", replyMarkup);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method of sending inLineKeyBoard for some important tasks again
    public String sendInlineKeyboard(String chatId, String text, String[][] buttons, String[][] callbackData) throws IOException {
        String url = API_URL + token + "/sendMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("text", text);

        JSONObject replyMarkup = new JSONObject();
        JSONArray inlineKeyboard = new JSONArray();

        for (int i = 0; i < buttons.length; i++) {
            JSONArray buttonRow = new JSONArray();
            for (int j = 0; j < buttons[i].length; j++) {
                JSONObject button = new JSONObject();
                button.put("text", buttons[i][j]);
                button.put("callback_data", callbackData[i][j]);
                buttonRow.put(button);
            }
            inlineKeyboard.put(buttonRow);
        }

        replyMarkup.put("inline_keyboard", inlineKeyboard);
        payload.put("reply_markup", replyMarkup);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for deleting particular message
    public String deleteMessage(String chatId, long messageId) throws IOException {
        String url = API_URL + token + "/deleteMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("message_id", messageId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method for editing an existing message
    public String editMessage(String chatId, long messageId, String newText) throws IOException {
        String url = API_URL + token + "/editMessageText";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("message_id", messageId);
        payload.put("text", newText);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for forwarding any kind of message to someone else
    public String forwardMessage(String chatId, String fromChatId, long messageId) throws IOException {
        String url = API_URL + token + "/forwardMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("from_chat_id", fromChatId);
        payload.put("message_id", messageId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method for sending location using latitude and longitude
    public String sendLocation(String chatId, double latitude, double longitude) throws IOException {
        String url = API_URL + token + "/sendLocation";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("latitude", latitude);
        payload.put("longitude", longitude);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for getting the chat member
    public String getChatMember(String chatId, String userId) throws IOException {
        String url = API_URL + token + "/getChatMember?chat_id=" + chatId + "&user_id=" + userId;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method of sending stickers
    public String sendSticker(String chatId, File sticker) throws IOException {
        String url = API_URL + token + "/sendSticker";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("sticker", sticker.getName(),
                        RequestBody.create(sticker, MediaType.parse("image/webp")))
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for sending voice
    public String sendVoice(String chatId, File voice, String caption) throws IOException {
        String url = API_URL + token + "/sendVoice";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("voice", voice.getName(),
                        RequestBody.create(voice, MediaType.parse("audio/ogg")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for bannig users
    public String banChatMember(String chatId, long userId) throws IOException {
        String url = API_URL + token + "/banChatMember";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("user_id", userId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    
    //Method for unbanning users
    public String unbanChatMember(String chatId, long userId) throws IOException {
        String url = API_URL + token + "/unbanChatMember";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("user_id", userId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for restricing any user
    public String restrictChatMember(String chatId, long userId, boolean canSendMessages) throws IOException {
        String url = API_URL + token + "/restrictChatMember";

        JSONObject permissions = new JSONObject();
        permissions.put("can_send_messages", canSendMessages);

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("user_id", userId);
        payload.put("permissions", permissions);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for promoting any user to become admin
    public String promoteChatMember(String chatId, long userId) throws IOException {
        String url = API_URL + token + "/promoteChatMember";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("user_id", userId);
        payload.put("can_change_info", true);
        payload.put("can_post_messages", true);
        payload.put("can_edit_messages", true);
        payload.put("can_delete_messages", true);
        payload.put("can_invite_users", true);
        payload.put("can_restrict_members", true);
        payload.put("can_pin_messages", true);
        payload.put("can_promote_members", true);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for setting the chat title
    public String setChatTitle(String chatId, String title) throws IOException {
        String url = API_URL + token + "/setChatTitle";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("title", title);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for setting the chat photo
    public String setChatPhoto(String chatId, File photo) throws IOException {
        String url = API_URL + token + "/setChatPhoto";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("photo", photo.getName(),
                        RequestBody.create(photo, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for leaving chat
    public String leaveChat(String chatId) throws IOException {
        String url = API_URL + token + "/leaveChat";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for sending animation
    public String sendAnimation(String chatId, File animation, String caption) throws IOException {
        String url = API_URL + token + "/sendAnimation";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("animation", animation.getName(),
                        RequestBody.create(animation, MediaType.parse("video/mp4")))
                .addFormDataPart("caption", caption)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for pinning any chat
    public String pinChatMessage(String chatId, long messageId) throws IOException {
        String url = API_URL + token + "/pinChatMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("message_id", messageId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for unpinning any chat 
    public String unpinChatMessage(String chatId, Long messageId) throws IOException {
        String url = API_URL + token + "/unpinChatMessage";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        if (messageId != null) {
            payload.put("message_id", messageId);
        }

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Some extra methods
    public String getChatAdministrators(String chatId) throws IOException {
        String url = API_URL + token + "/getChatAdministrators?chat_id=" + chatId;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for getting the member count
    public String getChatMembersCount(String chatId) throws IOException {
        String url = API_URL + token + "/getChatMemberCount?chat_id=" + chatId;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for getting files given by the members
    public String getFile(String fileId) throws IOException {
        String url = API_URL + token + "/getFile?file_id=" + fileId;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for creating poll
    public String sendPoll(String chatId, String question, String[] options) throws IOException {
        String url = API_URL + token + "/sendPoll";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);
        payload.put("question", question);

        JSONArray optionsArray = new JSONArray();
        for (String option : options) {
            optionsArray.put(option);
        } 
        payload.put("options", optionsArray);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for setting dice for users
    public String sendDice(String chatId) throws IOException {
        String url = API_URL + token + "/sendDice";

        JSONObject payload = new JSONObject();
        payload.put("chat_id", chatId);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Method for setting bot commands
    public String setMyCommands(String[][] commands) throws IOException {
        String url = API_URL + token + "/setMyCommands";

        JSONArray commandsArray = new JSONArray();
        for (String[] command : commands) {
            JSONObject cmd = new JSONObject();
            cmd.put("command", command[0]);
            cmd.put("description", command[1]);
            commandsArray.put(cmd);
        }

        JSONObject payload = new JSONObject();
        payload.put("commands", commandsArray);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}  