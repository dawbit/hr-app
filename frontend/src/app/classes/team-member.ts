export interface TeamMember {
  name: string;
  mail: string;
  photo: string;
  position: string;
  description: string;
  socialMedia?: {
    facebook?: string;
    twitter?: string;
    instagram?: string;
    youtube?: string;
    github?: string;
    linkedin?: string;
  };
}
