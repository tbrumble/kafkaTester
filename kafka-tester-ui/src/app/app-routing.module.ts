import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MessagesComponent } from './messages/messages.component';
import { PipesComponent } from './pipes/pipes.component';
import { ProjectsComponent } from './projects/projects.component';
import { SettingsComponent } from './settings/settings.component';
import { TestsComponent } from './tests/tests.component';

const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full'},
  { path: 'projects', component: ProjectsComponent },
  { path: 'messages', component: MessagesComponent },
  { path: 'tests', component: TestsComponent },
  { path: 'pipes', component: PipesComponent },
  { path: 'settings', component: SettingsComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
