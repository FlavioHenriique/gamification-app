import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  credentials: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthenticationService,
    private alertController: AlertController, 
    private router: Router,
    private loadingController: LoadingController
    ) { }

  ngOnInit() {
    this.credentials = this.fb.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['',[Validators.required, Validators.minLength(6)]]
    })
  }

  async login(){
    const loading = await this.loadingController.create();
    await loading.present();

    this.authService.login(this.credentials.value).subscribe(
      async(res) =>{
        console.log("logou");
        await loading.dismiss();
        this.router.navigateByUrl('/tabs', {replaceUrl: true});
      },
      async(res) =>{
        console.log(res);
        await loading.dismiss();
        const alert = await this.alertController.create({
          header: 'Erro',
          message: res.error.message,
          buttons: ['OK']
        });
        await alert.present();
      }
    )
  }

}