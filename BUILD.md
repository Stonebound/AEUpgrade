# Building AEUpgrade (2026 rebuild)

The original build (`README.md`) — ForgeGradle 1.2‑SNAPSHOT, Gradle 1.12, MC 1.7.2, AE2
`rv0-stable-1`, drone.io CI — no longer resolves; every one of those coordinates is gone.

This branch keeps the mod source **verbatim from upstream `5c102cb`** and only swaps the build
tooling:

- **RetroFuturaGradle 1.4.9**, Gradle 8.14.5 wrapper, run under **JDK 17 or 21** (RFG injects
  JDK 8 for the MC compile via the toolchain).
- Target **MC 1.7.10 / Forge 10.13.4.1614**, MCP `stable_12`.
- Compiles against **Applied Energistics 2 `rv3-beta-6`** (CurseForge project 223794, file
  2296430) pulled via CurseMaven and deobfuscated with `rfg.deobf`.

Source changes from upstream `5c102cb`:
- `blockQuartzPiller` → `blockQuartzPillar` in `BlockQuartzPillar.java` (AE2's rv0 API had the
  typo; rv3 fixed it).
- `common/base/AppEngMultiBlock.java`: the stub blocks were `Material.iron` / hardness 1.9,
  which mine slowly and need a pickaxe. They are throwaway migration stubs meant to be broken
  on sight — changed to `Material.rock` / hardness 0.2 with `canHarvestBlock` → true, so they
  break instantly by hand and always drop the AE2 gear.

Everything else in the rv0‑era API AEUpgrade uses (`appeng.api.definitions.Blocks/Items/Materials`,
`AEItemDefinition`) still exists in rv3‑beta‑6 as `@Deprecated` compat classes.

```sh
JAVA_HOME=/path/to/jdk21 ./gradlew build
# -> build/libs/aeupgrade-1.7.10-1.0.0.jar        (reobfuscated — this is the one that loads)
# -> build/libs/aeupgrade-1.7.10-1.0.0-dev.jar    (MCP-named)
```

AEUpgrade registers as modid `AppliedEnergistics` (AE1's id) on purpose, so AE1 world data
binds to its stub blocks/tiles. It is a **one‑shot converter** — install it in a throwaway
1.7.10 instance alongside AE2, break down every AE1 block/item into its AE2 equivalent, then
remove it and move the world to your real 1.7.10 pack. See `README.md` for the full
step‑by‑step (including the `appeng-rv14-upgrade.jar` pass you run on the 1.6.x side first).
