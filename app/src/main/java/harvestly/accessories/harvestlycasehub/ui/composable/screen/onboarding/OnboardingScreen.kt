package harvestly.accessories.harvestlycasehub.ui.composable.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import harvestly.accessories.harvestlycasehub.ui.viewmodel.DOFIPOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private data class OnboardingPage(val title: String, val description: String, val image: String)

private val pages = listOf(
    OnboardingPage(
        "Protection with personality",
        "Discover phone cases selected for reassuring protection, considered materials and a look that feels like yours.",
        "https://images.unsplash.com/photo-1603313011101-320f26a4f6f6?w=1400"
    ),
    OnboardingPage(
        "Everyday tech, made easier",
        "From quick charging to clear calls, find useful accessories that earn their place in your pocket and bag.",
        "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=1400"
    ),
    OnboardingPage(
        "Reserve now, collect today",
        "Build your basket and reserve in seconds. Your confirmed order will be waiting in store for the next 24 hours.",
        "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=1400"
    )
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: DOFIPOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val completed by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(completed) {
        if (completed) onNavigateToHomeScreen()
    }

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = page.image,
                    contentDescription = page.title,
                    modifier = Modifier.fillMaxSize().padding(top = 150.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)).padding(24.dp)
                ) {
                    Text(text = page.title, style = MaterialTheme.typography.headlineLarge)
                    Text(
                        text = page.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pages.indices.forEach { index ->
                    Box(
                        modifier = Modifier.size(if (pagerState.currentPage == index) 10.dp else 7.dp).clip(CircleShape)
                            .background(if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                    )
                }
            }
            Button(
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        viewModel.setOnboarded()
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                }
            ) {
                Text(if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Continue")
            }
        }
    }
}
