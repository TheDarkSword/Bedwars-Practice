package it.thedarksword.bedwarspractice.abstraction.sessions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SessionType {

    BRIDGING("BRIDGING"),
    WALL_CLUTCH("WALL CLUTCH"),
    KB_CLUTCH("KB CLUTCH");

    final String name;
}
