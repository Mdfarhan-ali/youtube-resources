import java.net.URI;
import java.net.http.*;
import java.util.regex.*;

public class AskGemini {
  public static void main(String[] args) throws Exception {
    String question = "full form of AI";
    String json = "{\"contents\":[{\"parts\":[{\"text\":\"" + question + "\"}]}]}";

    HttpRequest req = HttpRequest.newBuilder(URI.create(
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent"))
      .header("x-goog-api-key", System.getenv("GEMINI_API_KEY"))
      .header("content-type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(json))
      .build();

    HttpResponse<String> res = HttpClient.newHttpClient()
      .send(req, HttpResponse.BodyHandlers.ofString());

    if (res.statusCode() != 200) {
      System.out.println("Error " + res.statusCode() + ": " + res.body());
      return;
    }

    // Simple extraction of the first "text" field (use Jackson or Gson in real projects)
    Matcher m = Pattern.compile("\"text\":\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(res.body());
    System.out.println(m.find() ? m.group(1).replace("\\n", "\n") : res.body());
  }
}