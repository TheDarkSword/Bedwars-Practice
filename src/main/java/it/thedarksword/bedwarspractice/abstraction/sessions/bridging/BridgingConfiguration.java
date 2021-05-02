package it.thedarksword.bedwarspractice.abstraction.sessions.bridging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BridgingConfiguration {

    private final BridgeLength length;
    private final BridgeHeight height;
    private final BridgeDirection direction;

    @RequiredArgsConstructor
    @Getter
    public enum BridgeLength {
        SHORT(30, 15),
        MEDIUM(50, 35),
        LONG(100, 85),
        INFINITE(-1, -1);

        private final int xForward;
        private final int xDiagonal;
    }

    @RequiredArgsConstructor
    @Getter
    public enum BridgeHeight {
        NONE(0),
        SLIGHT(7),
        STAIRCASE(30);

        private final int y;
    }

    @RequiredArgsConstructor
    @Getter
    public enum BridgeDirection {
        FORWARD(0),
        DIAGONAL(15);

        private final int z;
    }
}
