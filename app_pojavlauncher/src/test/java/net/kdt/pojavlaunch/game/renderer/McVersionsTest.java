package net.kdt.pojavlaunch.game.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class McVersionsTest {
    @Test
    public void newNumberingIsModern() {
        assertTrue(McVersions.isModern("26.3"));
        assertTrue(McVersions.isModern("26.1"));
    }

    @Test
    public void releasesFrom117AreModern() {
        assertTrue(McVersions.isModern("1.17"));
        assertTrue(McVersions.isModern("1.17-pre1"));
        assertTrue(McVersions.isModern("1.21.4"));
    }

    @Test
    public void olderReleasesAreLegacy() {
        assertFalse(McVersions.isModern("1.16.5"));
        assertFalse(McVersions.isModern("1.12.2"));
        assertFalse(McVersions.isModern("1.7.10"));
    }

    @Test
    public void oldBetaAlphaAreLegacy() {
        assertFalse(McVersions.isModern("b1.7.3"));
        assertFalse(McVersions.isModern("a1.2.6"));
        assertFalse(McVersions.isModern("rd-132211"));
        assertFalse(McVersions.isModern("inf-20100618"));
    }

    @Test
    public void snapshotsSplitAt20w45a() {
        assertFalse(McVersions.isModern("20w14a"));
        assertTrue(McVersions.isModern("20w45a"));
        assertTrue(McVersions.isModern("25w14a"));
    }

    @Test
    public void unknownIdsDefaultToModern() {
        assertTrue(McVersions.isModern(null));
        assertTrue(McVersions.isModern("latest_release"));
        assertTrue(McVersions.isModern("my custom version"));
    }
}
