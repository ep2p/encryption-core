package lab.idioglossia.eleuth.encore.key;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NeoRoutesCNGenerator implements CNGenerator {
    private final String userId;

    @Override
    public String generate() {
        return "cn="+ userId + ".neoroutes";
    }
}
