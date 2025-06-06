import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.data.model.AppInfo
import com.gsrajeni.appscheduler.ui.components.AppIconFromPackage
import com.gsrajeni.appscheduler.ui.screens.add_schedule.AddScheduleViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalledAppsBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onAppClick: (AppInfo) -> Unit = {}
) {
    val viewModel: AddScheduleViewModel = hiltViewModel()
    val appList by viewModel.installedAppList.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
        ) { // Add navigationBarsPadding for content behind nav bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Installed Apps", style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (appList.isEmpty() && sheetState.isVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.7f) // Adjust height as needed
                ) {
                    items(appList.size) { app ->
                        Column(modifier = Modifier.clickable {
                            onAppClick(appList[app])
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppIconFromPackage(
                                    appList[app].packageName,
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(24.dp)
                                )
                                Text(
                                    text = appList[app].name,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                            }
                            if (app != appList.size - 1)
                                HorizontalDivider()
                        }
                    }
                }
                // Add a spacer at the bottom if content might go behind the system navigation bar
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}