
# IdleShutdown

Shutdown a Minecraft Fabric server after a designated period
of inactivity.

### Config Options

- `delayMilliseconds` : The amount of time (in milliseconds) 
that the server should shut off after the last player has left.
(Default: 604800000 (7 days); Minimum: 0; Maximum: 9223372036854775807)

### Development

(Using IntelliJ) run the Minecraft Client and Minecraft Server run configurations.  
Run the `build` gradle task to output build files to `build/libs/`.
