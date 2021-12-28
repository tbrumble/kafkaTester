import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProjectComponent } from './project/project.component';
import { ProjectPanelComponent } from './project-panel/project-panel.component';
import { ProjectsComponent } from './projects/projects.component';
import { TestsComponent } from './tests/tests.component';
import { MessagesComponent } from './messages/messages.component';
import { PipesComponent } from './pipes/pipes.component';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    AppComponent,
    ProjectComponent,
    ProjectPanelComponent,
    ProjectsComponent,
    TestsComponent,
    MessagesComponent,
    PipesComponent,
    SettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
