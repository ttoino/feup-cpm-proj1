package pt.up.fe.cpm.tiktek

import dagger.hilt.android.AndroidEntryPoint
import pt.up.fe.cpm.tiktek.core.ui.ScreenActivity

@AndroidEntryPoint
class MainActivity : ScreenActivity({ MainScreen() })
