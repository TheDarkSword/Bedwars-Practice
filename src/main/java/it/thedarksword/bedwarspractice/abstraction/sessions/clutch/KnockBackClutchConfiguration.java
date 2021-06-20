package it.thedarksword.bedwarspractice.abstraction.sessions.clutch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KnockBackClutchConfiguration {

    EASY(0.05, 0.1, 0.15, 0.2, "Facile"),
    MEDIUM(0.066, 0.133, 0.2, 0.266, "Medio"),
    HARD(0.1, 0.2, 0.3, 0.4, "Difficile");

    private final double xMin, xMax, zMin, zMax;
    private final String name;
}
