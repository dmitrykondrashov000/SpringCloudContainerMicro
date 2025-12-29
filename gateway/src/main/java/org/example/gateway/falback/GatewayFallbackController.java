package org.example.gateway.falback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/fallback/users", method =
        {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class GatewayFallbackController {

    @GetMapping("/users")
    public ResponseEntity<String> userFallback() {
        // Можно вернуть JSON
        return ResponseEntity
                .ok("⚠️ User‑service временно недоступен. Попробуйте позже.");
    }
}
