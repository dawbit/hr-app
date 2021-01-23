import { Component, OnInit } from '@angular/core';
import { TeamMember } from './../../../../classes/team-member';
import * as dawidBitnerJson from '../../../../../assets/teamPhotos/json/dawidBitner.json';
import * as danielBroczkowskiJson from '../../../../../assets/teamPhotos/json/danielBroczkowski.json';
import * as mateuszKowolJson from '../../../../../assets/teamPhotos/json/mateuszKowol.json';

@Component({
  selector: 'app-contact-team',
  templateUrl: './contact-team.component.html',
  styleUrls: ['./contact-team.component.scss']
})
export class ContactTeamComponent implements OnInit {

  constructor() { }
  members: TeamMember[];
  dawidBitner: TeamMember;
  danielBroczkowski: TeamMember;
  mateuszKowol: TeamMember;

  photoPath: './../../../../../../../team/';

  ngOnInit(): void {
    this.dawidBitner = Object.assign(dawidBitnerJson).default;
    this.danielBroczkowski = Object.assign(danielBroczkowskiJson).default;
    this.mateuszKowol = Object.assign(mateuszKowolJson).default;
    this.members = [this.dawidBitner, this.danielBroczkowski, this.mateuszKowol];
  }

}
