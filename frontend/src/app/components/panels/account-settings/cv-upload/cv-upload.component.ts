import { FtpService } from './../../../../services/ftp.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cv-upload',
  templateUrl: './cv-upload.component.html',
  styleUrls: ['./cv-upload.component.scss']
})
export class CvUploadComponent implements OnInit {
  fileData: File = null;

  constructor(private ftpService: FtpService) { }

  ngOnInit() {
  }

fileProgress(fileInput: any) {
    this.fileData = (fileInput.target.files[0] as File);
}

onSubmit() {
    const formData = new FormData();
    formData.append('file', this.fileData);
    this.ftpService.addCv(formData).subscribe(res => {
    console.log('success');
  });
}

}
