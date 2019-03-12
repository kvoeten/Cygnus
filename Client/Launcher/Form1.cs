using Newtonsoft.Json;
using RestSharp;
using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Launcher
{
    public partial class Form1 : Form
    {
        private static account pAccount;

        public Form1()
        {
            InitializeComponent();
        }

        internal static account PAccount { get => pAccount; set => pAccount = value; }

        private void button1_ClickAsync(object sender, EventArgs e)
        {
            login();
        }

        private void login()
        {
            String username = textBox1.Text;
            String password = textBox2.Text;
            String client_id = "2";
            String client_secret = "WnlXJ34B9nhIBjf5oAg12OwdLT6lopGs2cewCuVc";

            if (pAccount == null)
            {
                var client = new RestClient(Program.APIHost + "oauth/token");
                var request = new RestRequest(Method.POST);
                string content = string.Format("grant_type=password&password={0}&username={1}&client_secret={2}&client_id={3}", password, username, client_secret, client_id);
                request.AddHeader("content-type", "application/x-www-form-urlencoded");
                request.AddParameter("application/x-www-form-urlencoded", content, ParameterType.RequestBody);

                try
                {
                    IRestResponse response = client.Execute(request);
                    account user = JsonConvert.DeserializeObject<account>(response.Content);
                    if (user == null || user.access_token == null || user.access_token == "")
                    {
                        MessageBox.Show(
                            response.StatusCode + "\r\n" 
                            + response.StatusDescription + "\r\n" 
                            + response.ErrorMessage + "\r\n" 
                            + response.Content + "\r\n"
                            + content, 
                            "Response Error", 
                            MessageBoxButtons.OK, 
                            MessageBoxIcon.Error);
                    }
                    else
                    {
                        pAccount = user;
                        textBox3.Text = pAccount.access_token;
                    }
                }
                catch (WebException ex)
                {
                    MessageBox.Show("Unable to request account details!\r\n" + ex, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }

            string path = Directory.GetCurrentDirectory();
            if (!File.Exists(path + "\\MapleStory.exe"))
            {
                MessageBox.Show("Unable to locate MapleStory.exe.\r\n\r\nPlease move this client into your maplestory folder.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                ProcessStartInfo startInfo = new ProcessStartInfo(string.Concat(path, "\\", "MapleStory.exe"));
                startInfo.Arguments = "WEBSTART " + pAccount.access_token + " 8.31.99.141 8484";
                startInfo.UseShellExecute = false;
                Process.Start(startInfo);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            pAccount = null;
            textBox3.Text = "";
        }

        private string Base64Encode(string plainText)
        {
            var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(plainText);
            return System.Convert.ToBase64String(plainTextBytes);
        }
    }

    class account
    {
        public string access_token;
        public string token_type;
        public string refresh_token;
        public string expires_in;
    }
}
