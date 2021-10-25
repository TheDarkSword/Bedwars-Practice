package it.thedarksword.bedwarspractice.abstraction.sessions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SessionType {

    BRIDGING("BRIDGING"),
    WALL_CLUTCH("WALL CLUTCH"),
    KB_CLUTCH("KB CLUTCH"),
    LAUNCH("LAUNCH"),
    BED_BURROW("BED BURROW");

    final String name;

    @Override
    public String toString() {
        return name;
    }
}
