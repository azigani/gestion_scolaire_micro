import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule)
  },
  {
    path: 'students',
    loadChildren: () => import('./features/students/students.module').then(m => m.StudentsModule)
  },
  {
    path: 'classes',
    loadChildren: () => import('./features/classes/classes.module').then(m => m.ClassesModule)
  },
  {
    path: 'academic',
    loadChildren: () => import('./features/academic/academic.module').then(m => m.AcademicModule)
  },
  {
    path: 'financial',
    loadChildren: () => import('./features/financial/financial.module').then(m => m.FinancialModule)
  },
  {
    path: 'hr',
    loadChildren: () => import('./features/hr/hr.module').then(m => m.HrModule)
  },
  {
    path: 'communication',
    loadChildren: () => import('./features/communication/communication.module').then(m => m.CommunicationModule)
  },
  {
    path: 'settings',
    loadChildren: () => import('./features/settings/settings.module').then(m => m.SettingsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
