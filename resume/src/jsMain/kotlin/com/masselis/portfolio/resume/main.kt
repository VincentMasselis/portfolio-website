package com.masselis.portfolio.resume

import androidx.compose.runtime.Composable
import com.masselis.portfolio.data.PortfolioData
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.renderComposable

fun main() {
    // Inject CSS via a <style> element
    val styleElement = document.createElement("style")
    styleElement.textContent = RESUME_CSS
    document.head?.appendChild(styleElement)

    renderComposable(rootElementId = "root") {
        ResumePage()
    }
}

@Composable
private fun ResumePage() {
    Div({ classes("resume-container") }) {
        ResumeHeader()
        SkillsSection()
        TimelineSection()
        ProjectsSection()
        ContactsSection()
    }
    Div({ classes("print-btn") }) {
        Button({
            onClick { window.print() }
        }) {
            Text("Enregistrer en PDF")
        }
    }
}

@Composable
private fun ResumeHeader() {
    Div({ classes("resume-header") }) {
        H1 { Text("VINCENT MASSELIS") }
        Div({ classes("subtitle") }) {
            Text("D\u00C9VELOPPEUR ANDROID & iOS \u2022 KOTLIN & COMPOSE MULTIPLATFORM \u2022 SOFTWARE ARCHITECT")
        }
        Div({ classes("contact-line") }) {
            Text("linkedin.com/in/vincentmasselis \u2022 github.com/RxVincent \u2022 vincent@rxvincent.com \u2022 Lille, France")
        }
    }
}

@Composable
private fun SkillsSection() {
    Section({ classes("resume-section") }) {
        H2 { Text("COMP\u00C9TENCES TECHNIQUES") }
        Div({ classes("skills-grid") }) {
            PortfolioData.skills.forEach { skill ->
                Div({ classes("skill-item") }) {
                    Span({ classes("skill-label") }) {
                        Text("${skill.name} \u2014 ${(skill.level.fraction * 100).toInt()}%")
                    }
                    Div({ classes("skill-bar-bg") }) {
                        Div({
                            classes("skill-bar-fill")
                            style { width((skill.level.fraction * 100).percent) }
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineSection() {
    Section({ classes("resume-section") }) {
        H2 { Text("POINTS FORTS DE CARRI\u00C8RE") }
        PortfolioData.timelineEntries.forEach { entry ->
            Div({ classes("timeline-entry") }) {
                Div({ classes("timeline-date") }) { Text(entry.time.toString()) }
                Div({ classes("timeline-title") }) { Text(entry.title) }
                Div({ classes("timeline-desc") }) { Text(entry.description) }
            }
        }
    }
}

@Composable
private fun ProjectsSection() {
    Section({ classes("resume-section") }) {
        H2 { Text("R\u00C9ALISATIONS") }
        PortfolioData.projects.forEach { project ->
            Div({ classes("project-card") }) {
                H3 { Text(project.title) }
                //P { Text(project.description.toString()) }
                Ul {
                    project.bulletPoints.forEach { point ->
                        Li { Text(point) }
                    }
                }
                Div({ classes("tech-stack") }) {
                    project.skills.forEach { tech ->
                        Span({ classes("tech-tag") }) { Text(tech.name) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactsSection() {
    Section({ classes("resume-section") }) {
        H2 { Text("CONTACT") }
        Div({ classes("contacts-grid") }) {
            /*PortfolioData.contacts.forEach { contact ->
                Div({ classes("contact-item") }) {
                    Span({ classes("label") }) { Text("${contact.iconName}: ") }
                    Text(contact.url)
                }
            }*/
        }
    }
}

private val RESUME_CSS = """
*,*::before,*::after{box-sizing:border-box}
body{font-family:monospace;margin:0;padding:0;background:#F5F5F5;color:#1C1C1C;line-height:1.6}
.resume-container{max-width:900px;margin:0 auto;background:#fff;box-shadow:0 2px 8px rgba(0,0,0,0.1)}
.resume-header{background:#1C2E3F;color:#fff;padding:40px;text-align:center}
.resume-header h1{margin:0;font-size:36px;letter-spacing:4px;font-weight:700}
.resume-header .subtitle{font-size:16px;margin-top:8px;color:#4CAF50;letter-spacing:2px}
.resume-header .contact-line{font-size:13px;margin-top:16px;color:#B0BEC5}
.resume-section{padding:32px 40px}
.resume-section h2{font-size:20px;letter-spacing:2px;color:#1C2E3F;border-bottom:2px solid #4CAF50;padding-bottom:8px;margin-top:0;margin-bottom:24px}
.skills-grid{display:flex;flex-wrap:wrap;gap:12px}
.skill-item{display:flex;flex-direction:column;width:48%}
.skill-label{font-size:13px;font-weight:600;margin-bottom:4px}
.skill-bar-bg{background:#E0E0E0;border-radius:4px;height:8px;width:100%}
.skill-bar-fill{background:#4CAF50;border-radius:4px;height:8px}
.timeline-entry{margin-bottom:20px;padding-left:16px;border-left:3px solid #4CAF50}
.timeline-date{font-size:12px;color:#4CAF50;font-weight:700}
.timeline-title{font-size:15px;font-weight:700;margin-top:2px}
.timeline-desc{font-size:13px;color:#555;margin-top:2px}
.project-card{border:1px solid #E0E0E0;border-radius:8px;padding:20px;margin-bottom:16px}
.project-card h3{margin:0;font-size:16px;color:#1C2E3F}
.project-card p{font-size:13px;color:#555;margin-top:4px;margin-bottom:8px}
.project-card ul{margin:0;padding-left:20px}
.project-card li{font-size:13px;margin-bottom:2px}
.tech-stack{display:flex;flex-wrap:wrap;gap:6px;margin-top:8px}
.tech-tag{font-size:11px;background:#E8F5E9;color:#2E7D32;padding:2px 8px;border-radius:4px;font-weight:600}
.contacts-grid{display:flex;flex-wrap:wrap;gap:16px}
.contact-item{font-size:13px}
.contact-item .label{font-weight:700;color:#1C2E3F}
.print-btn{display:flex;justify-content:center;align-items:center;padding:24px}
.print-btn button{background:#4CAF50;color:#fff;border:0;padding:12px 32px;font-size:14px;font-family:monospace;font-weight:700;border-radius:6px;letter-spacing:1px;cursor:pointer}
.print-btn button:hover{background:#388E3C}
@media print{.print-btn{display:none}.resume-container{box-shadow:none;max-width:100%}body{background:#fff}}
""".trimIndent()
